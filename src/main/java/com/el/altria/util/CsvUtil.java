package com.el.altria.util;

import com.el.altria.constant.AltriaContants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class CsvUtil {
    public static <T> List<T> read(File file, Class<T> targetClass, LinkedHashMap<String, Integer> columnMap, @Nullable String DateTimeFormat) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            return Collections.emptyList();
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        AtomicInteger total = new AtomicInteger(0);
        SimpleDateFormat sdf = DateTimeFormat == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : new SimpleDateFormat(DateTimeFormat);
        List<T> result = br.lines()
                .map(line -> {
                    //skip title row
                    if (total.incrementAndGet() == 1) {
                        return null;
                    }


                    T obj = null;
                    try {
                        T temp = targetClass.getDeclaredConstructor().newInstance();
                        int startIdx = 0;
                        Iterator<Map.Entry<String, Integer>> entryIterator = columnMap.entrySet().iterator();
                        int paramCount = 0;
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) == ',' || i == line.length() - 1) {
                                paramCount++;
                                String param;
                                //handle data with double quotation
                                if (line.charAt(startIdx) == '"') {
                                    for (int k = i + 1; line.charAt(k - 1) != '"' && line.charAt(k) != ',' && k <= line.length() - 1; k++) {
                                        i = k + 1;
                                    }
                                    param = line.substring(startIdx + 1, i - 1);
                                } else {
                                    param = line.substring(startIdx, paramCount == columnMap.size() ? ++i : i);
                                }
                                startIdx = i + 1;

                                // assign value to object
                                if (!entryIterator.hasNext()) {
                                    entryIterator = columnMap.entrySet().iterator();
                                }
                                Map.Entry<String, Integer> mappingEntry = entryIterator.next();

                                if (StringUtils.isBlank(param) && !mappingEntry.getValue().equals(AltriaContants.CSV_COL_TYPE_STRING)) {
                                    continue;
                                }

                                switch (mappingEntry.getValue()) {
                                    case AltriaContants.CSV_COL_TYPE_INTEGER:
                                        FieldUtils.writeField(temp, mappingEntry.getKey(), Integer.valueOf(param), true);
                                        break;
                                    case AltriaContants.CSV_COL_TYPE_DOUBLE:
                                        FieldUtils.writeField(temp, mappingEntry.getKey(), Double.valueOf(param), true);
                                        break;
                                    case AltriaContants.CSV_COL_TYPE_STRING:
                                        FieldUtils.writeField(temp, mappingEntry.getKey(), param, true);
                                        break;
                                    case AltriaContants.CSV_COL_TYPE_DATE:
                                        FieldUtils.writeField(temp, mappingEntry.getKey(), sdf.parse(param), true);
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + mappingEntry.getValue());
                                }
                            }
                        }

                        obj = temp;
                    } catch (Exception e) {
                        log.warn("Failed to read line :  " + line, e);
                    }
                    return obj;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.info("Successfully read csv file {} of {} lines", result.size(), total.intValue());
        return result;
    }
}
