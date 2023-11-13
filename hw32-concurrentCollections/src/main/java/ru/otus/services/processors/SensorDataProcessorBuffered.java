package ru.otus.services.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private ArrayBlockingQueue<SensorData> blockingQueue;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.blockingQueue = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        blockingQueue.add(data);
        if (blockingQueue.size() >= bufferSize) {
            log.info("Очищаем буфер");
            flush();
        }
    }

    public void flush() {
        try {
            if (blockingQueue.isEmpty()) {
                return;
            }
            List<SensorData> sensorDataList = new ArrayList<>();
            synchronized (sensorDataList) {
                blockingQueue.drainTo(sensorDataList);
                sensorDataList.sort(Comparator.comparing(SensorData::getMeasurementTime));
                writer.writeBufferedData(sensorDataList);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
