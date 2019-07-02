package sample;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Krekic
 */

class FileContent {

    private String fileName;
    private Map<String, Integer> searchedSequence;

    FileContent() {
        this.searchedSequence = new HashMap<>();
    }

    String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    Map<String, Integer> getSearchedSequence() {
        return searchedSequence;
    }
}
