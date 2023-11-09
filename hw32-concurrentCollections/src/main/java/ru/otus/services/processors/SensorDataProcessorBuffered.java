package ru.otus.services.processors;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private List<SensorData> sensorDataList;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.sensorDataList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        synchronized (this) {
            sensorDataList.add(data);
            if (sensorDataList.size() >= bufferSize) {
                log.info("Очищаем буфер");
                flush();
            }
        }
    }

    public void flush() {
        try {
            synchronized (this) {
                if (sensorDataList.isEmpty()) {
                    return;
                }
                sensorDataList.sort(Comparator.comparing(SensorData::getMeasurementTime));
                writer.writeBufferedData(sensorDataList);
                sensorDataList = new CopyOnWriteArrayList<>();
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
