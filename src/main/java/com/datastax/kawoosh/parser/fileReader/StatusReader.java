//package com.datastax.kawoosh.parser.fileReader;
//
//import com.datastax.kawoosh.common.Tuple;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Stream;
//
//public class StatusReader implements Reader {
//
//    public static final List<String> NODESTATUS = List.of("UN", "UL", "UJ", "UM", "DN", "DS", "DL", "DJ", "DM");
//    @Override
//    public Stream<Tuple> read(String path) {
//        boolean dc_valid = false;
//        boolean nodes_valid = false;
//        Stream.Builder<Tuple> result = Stream.<Tuple>builder();
//        try {
//            Scanner scanner = new Scanner(path);
//            String dcName = "";
//
//            while (scanner.hasNextLine()) {
//                String currentLine = scanner.nextLine();
//                String[] splitLine = currentLine.split(" ");
//                if (currentLine.toLowerCase().contains("Datacenter")) {
//                    dcName = splitLine[1];
//                    dc_valid = true;
//                }
//                if (NODESTATUS.contains(splitLine[0]) && splitLine.length >= 7) {
//                    String rack = "";
//
//                    nodeInfo.put(ValFactory.UD, splitLine[0]);
//                    padding.put(ValFactory.UD, (Integer) padding.get(ValFactory.UD) > splitLine[0].length() + ValFactory.PAD
//                            ? padding.get(ValFactory.UD) : splitLine[0].length() + ValFactory.PAD);
//                    nodeInfo.put(ValFactory.ADDRESS, splitLine[1]);
//                    padding.put(ValFactory.ADDRESS, (Integer) padding.get(ValFactory.ADDRESS) > splitLine[1].length() + ValFactory.PAD
//                            ? padding.get(ValFactory.ADDRESS) : splitLine[1].length() + ValFactory.PAD);
//                    nodeDCMap.put(splitLine[1], dcName);
//                    if (splitLine[2].equals("?")) {
//                        nodeInfo.put(ValFactory.LOAD, splitLine[2]);
//                        nodeLoadMap.put(splitLine[1], splitLine[2]);
//                        padding.put(ValFactory.LOAD, (Integer) padding.get(ValFactory.LOAD) > splitLine[2].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.LOAD) : splitLine[2].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.TOKENS, splitLine[3]);
//                        padding.put(ValFactory.TOKENS, (Integer) padding.get(ValFactory.TOKENS) > splitLine[3].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.TOKENS) : splitLine[3].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.OWNS, splitLine[4]);
//                        padding.put(ValFactory.OWNS, (Integer) padding.get(ValFactory.OWNS) > splitLine[4].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.OWNS) : splitLine[4].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.HOST_ID, splitLine[5]);
//                        padding.put(ValFactory.HOST_ID, (Integer) padding.get(ValFactory.HOST_ID) > splitLine[5].length() + ValFactory.PAD ?
//                                padding.get(ValFactory.HOST_ID) : splitLine[5].length() + ValFactory.PAD);
//                        for (int i = 6; i < splitLine.length; i++) {
//                            if (i == (splitLine.length - 1)) {
//                                rack += splitLine[i];
//                            } else {
//                                rack += splitLine[i] + " ";
//                            }
//                        }
//                    } else {
//                        nodeInfo.put(ValFactory.LOAD, splitLine[2] + " " + splitLine[3]);
//                        nodeLoadMap.put(splitLine[1], splitLine[2] + " " + splitLine[3]);
//                        padding.put(ValFactory.LOAD, (Integer) padding.get(ValFactory.LOAD) > splitLine[2].length() + 1 + splitLine[3].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.LOAD) : splitLine[2].length() + 1 + splitLine[3].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.TOKENS, splitLine[4]);
//                        padding.put(ValFactory.TOKENS, (Integer) padding.get(ValFactory.TOKENS) > splitLine[4].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.TOKENS) : splitLine[4].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.OWNS, splitLine[5]);
//                        padding.put(ValFactory.OWNS, (Integer) padding.get(ValFactory.OWNS) > splitLine[5].length() + ValFactory.PAD
//                                ? padding.get(ValFactory.OWNS) : splitLine[5].length() + ValFactory.PAD);
//                        nodeInfo.put(ValFactory.HOST_ID, splitLine[6]);
//                        padding.put(ValFactory.HOST_ID, (Integer) padding.get(ValFactory.HOST_ID) > splitLine[6].length() + ValFactory.PAD ?
//                                padding.get(ValFactory.HOST_ID) : splitLine[6].length() + ValFactory.PAD);
//                        for (int i = 7; i < splitLine.length; i++) {
//                            if (i == (splitLine.length - 1)) {
//                                rack += splitLine[i];
//                            } else {
//                                rack += splitLine[i] + " ";
//                            }
//                        }
//                    }
//                    nodeInfo.put(ValFactory.RACK, rack);
//                    padding.put(ValFactory.RACK, (Integer) padding.get(ValFactory.RACK) > rack.length() + ValFactory.PAD
//                            ? padding.get(ValFactory.RACK) : rack.length() + ValFactory.PAD);
//                    nodeArray.add(nodeInfo);
//
//                    nodes_valid = true;
//                }
//
//                if (ValFactory.NODESTATUS.contains(splitLine[0]) && splitLine.length < 8) {
//                    nodes_valid = false;
//                    break;
//                }
//            }
//
//            if (nodes_valid == false || dc_valid == false) {
//                dc_valid = false;
//                nodes_valid = false;
//                continue;
//            }
//            dcInfo.put(ValFactory.NODES, nodeArray);
//            dcInfo.put(ValFactory.PADDING, padding);
//            nodeJSONArray.add(dcInfo);
//            nodetoolStatusJSON.put(ValFactory.FILE_PATH, file.getAbsolutePath());
//            nodetoolStatusJSON.put(ValFactory.FILE_NAME, file.getName());
//            nodetoolStatusJSON.put(ValFactory.FILE_ID, Inspector.getFileID(file));
//            nodetoolStatusJSON.put(ValFactory.STATUS, nodeJSONArray);
//        } catch (FileNotFoundException fnfe) {
//            logCheckedException(logger, fnfe);
//        }
//        return result.build();
//    }
//}
