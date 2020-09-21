package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeleteErrorLogFiles {

    public static void main(String[] args) {
        String fileStr = "C:\\ProgramData\\consul\\logs";
        File file = new File(fileStr);
        DeleteFilesThread deleteFilesThread = new DeleteFilesThread(file);
        deleteFilesThread.run();
    }


    static class DeleteFilesThread implements Runnable {
        private List<File> taskList = new CopyOnWriteArrayList<>();

        DeleteFilesThread(File file) {
            this(Arrays.asList(file.listFiles()));
        }

        DeleteFilesThread(List<File> files) {
            this.taskList.addAll(files);
        }

        @Override
        public void run() {
            List<List<File>> lists = splitList(taskList, 1000);
            for (List<File> f : lists) {
                new Thread(() -> {
                    delFiles(f);
                }).start();
            }
        }

        private boolean delFiles(List<File> f) {
            boolean result = false;
            for (File file : f) {
                if (file.isDirectory()) {
                    File[] childrenFiles = file.listFiles();
                    delFiles(Arrays.asList(childrenFiles));
                }
                result = file.delete();
                System.out.println(Thread.currentThread().getName() + ":删除文件：" + file.getName());
            }
            return result;
        }

        private List<List<File>> splitList(List<File> files, int groupSize) {
            int length = files.size();
            int num = (length + groupSize - 1) / groupSize;
            List<List<File>> newList = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                //开始位置
                int fromIndex = i * groupSize;
                // 结束位置
                int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
                newList.add(files.subList(fromIndex, toIndex));
            }
            return newList;
        }
    }
}
