package com.miracle.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author miracle_
 * Created at 2019-09-10 14:26
 */
public class Test {

    private static final String GOODS_ID = "goodsId";
    private static final String PRICE = "price";
    private static final String NUMBER = "number";
    private static final Integer TOTAL_TEMP_FILE = 40;
    private static final String FILE_FOLDER = "D:\\temp\\";
    private static final String FILE_PREFIX = "pre_";
    private static final String SUFFIX = ".csv";

    public static void main(String[] args) {
        // split();
        getFullRecords();
    }

    public static List<FullRecord> getFullRecords() {
        try {
            List<FullRecord> list = new ArrayList<>();
            File file = new File(FILE_FOLDER);
            for (File fi : file.listFiles()) {
                CSVParser parse = CSVParser.parse(new FileReader(fi), CSVFormat.DEFAULT);
                Iterator<CSVRecord> iterator = parse.iterator();
                while (iterator.hasNext()) {
                    List<FullRecord> sub = new ArrayList<>();
                    CSVRecord next = iterator.next();
                    FullRecord fullRecord = new FullRecord(next.get(0), Double.valueOf(next.get(1)), Integer.valueOf(next.get(2)));
                    sub.add(fullRecord);
                    list.addAll(sub);
                }
                if (list.size() > 1000) {
                    list = list.stream().sorted(Comparator.comparing(FullRecord::getVolume).reversed()).limit(1000).collect(Collectors.toList());
                }
                if (!parse.isClosed()) {
                    parse.close();
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }



    /**
     *
     * 分割数据
     */
    public static void split() {
        try {
            FileReader fileReader = new FileReader(new File("C:\\Users\\miracle\\Desktop\\test.csv"));
            CSVParser parse = CSVParser.parse(fileReader, CSVFormat.DEFAULT);
            Map<String, Map<Integer, Record>> map = new HashMap<>();
            for (int i = 0; i < TOTAL_TEMP_FILE; i++) {
                map.put(FILE_PREFIX + i + SUFFIX, new HashMap<>());
            }
            Iterator<CSVRecord> iterator = parse.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                CSVRecord next = iterator.next();
                if (null == next) {
                    continue;
                }
                Integer hashCode = next.get(3).hashCode();
                Double price = Double.valueOf(next.get(4));
                Integer number = Integer.valueOf(next.get(5));
                int key = hashCode % TOTAL_TEMP_FILE;
                Map<Integer, Record> tempMap = map.get(FILE_PREFIX + Math.abs(key) + SUFFIX);
                System.out.println(count + "==" + key + next.get(3));
                if (tempMap.containsKey(hashCode)) {
                    tempMap.put(hashCode, new Record(tempMap.get(hashCode).getVolume() + price * number, tempMap.get(hashCode).getNumber() + number));
                } else {
                    tempMap.put(hashCode, new Record(price * number, number));
                }

                count++;
                if (count == 1000000 || !iterator.hasNext()) {
                    for (String file : map.keySet()) {
                        Map<Integer, Record> record = map.get(file);
                        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(new File(FILE_FOLDER + file)), CSVFormat.DEFAULT);
                        for (Map.Entry<Integer, Record> entry : record.entrySet()) {
                            csvPrinter.printRecord(entry.getKey(), entry.getValue().volume, entry.getValue().getNumber());
                        }
                        csvPrinter.close();
                        record.clear();
                    }
                    count = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Record {

    Double volume;
    Integer number;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class FullRecord {
    String goodsId;
    Double volume;
    Integer number;

}


