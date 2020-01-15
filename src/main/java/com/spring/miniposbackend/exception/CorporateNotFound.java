package com.spring.miniposbackend.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorporateNotFound extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> extensions = new HashMap<>();

    public CorporateNotFound(String message, int invalidBookId) {
        super(message);
        extensions.put("cate_id", invalidBookId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

}
