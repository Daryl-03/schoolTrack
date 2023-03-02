package com.schooltrack.csv;

import com.schooltrack.exceptions.CSVReaderException;

import java.io.File;
import java.util.List;

public interface CSVReader <T>{
    public List<String> readFile(File file) throws CSVReaderException;
    public List<String []> getData(List<String> lines);
    public List<T> csvToObjects(List<String []> data) throws CSVReaderException;
}
