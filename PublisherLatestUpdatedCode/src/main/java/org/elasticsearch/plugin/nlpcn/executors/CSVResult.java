package org.elasticsearch.plugin.nlpcn.executors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eliran on 27/12/2015.
 */
public class CSVResult implements Serializable {
   
	private static final long serialVersionUID = 5509547807559834350L;
	
	private final List<String> headers;
    private final List<String> lines;

    public CSVResult(List<String> headers, List<String> lines) {
        this.headers = headers;
        this.lines = lines;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getLines() {
        return lines;
    }
}
