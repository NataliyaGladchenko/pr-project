package com.example.prproject.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.MappingException;
import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@Component
public class RestResponseStatusExceptionResolver extends DefaultHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                return handleIllegalArgument((IllegalArgumentException) ex, response, handler);
            }
            if (ex instanceof MappingException) {
                return handleModelMapping((MappingException) ex, response, handler);
            }
            if (ex instanceof EntityNotFoundException) {
                return handleEntityNotFound((EntityNotFoundException) ex, response, handler);
            }
            if (ex instanceof PersistenceException) {
                return handlePersistence((PersistenceException) ex, response, handler);
            }
            if (ex instanceof DataAccessException) {
                return handleDataAccess((DataAccessException) ex, response, handler);
            }
            if (ex instanceof BadCredentialsException) {
                return handleBadCredentials((BadCredentialsException) ex, response, handler);
            }
            if (ex instanceof AuthenticationException) {
                return handleAuthentication((AuthenticationException) ex, response, handler);
            }
            if (ex instanceof AccessDeniedException) {
                return handleAccessDenied((AccessDeniedException) ex, response, handler);
            }
            if (ex instanceof ExpiredJwtException) {
                return handleExpiredJwt((ExpiredJwtException) ex, response, handler);
            }
            ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
            return modelAndView != null ? modelAndView : handleException(ex, response, handler);
        } catch (Exception handlerEx) {
            logger.warn("Failure while trying to resolve exception [" + ex.getClass().getName() + "]", handlerEx);
        }
        return null;
    }

    private ModelAndView handleIllegalArgument(IllegalArgumentException ex,
                                               HttpServletResponse response,
                                               @Nullable Object handler) throws IOException {
        response.sendError(SC_BAD_REQUEST, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleModelMapping(MappingException ex,
                                            HttpServletResponse response,
                                            @Nullable Object handler) throws IOException {
        response.sendError(SC_BAD_REQUEST, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleDataAccess(DataAccessException ex,
                                          HttpServletResponse response,
                                          @Nullable Object handler) throws IOException {
        response.sendError(SC_BAD_REQUEST, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handlePersistence(PersistenceException ex,
                                           HttpServletResponse response,
                                           @Nullable Object handler) throws IOException {
        response.sendError(SC_BAD_REQUEST, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleEntityNotFound(EntityNotFoundException ex,
                                              HttpServletResponse response,
                                              @Nullable Object handler) throws IOException {
        response.sendError(SC_NOT_FOUND, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleBadCredentials(BadCredentialsException ex,
                                              HttpServletResponse response,
                                              @Nullable Object handler) throws IOException {
        response.sendError(SC_FORBIDDEN, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleAccessDenied(AccessDeniedException ex,
                                            HttpServletResponse response,
                                            @Nullable Object handler) throws IOException {
        response.sendError(SC_FORBIDDEN, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleAuthentication(AuthenticationException ex,
                                              HttpServletResponse response,
                                              @Nullable Object handler) throws IOException {
        response.sendError(SC_UNAUTHORIZED, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleExpiredJwt(ExpiredJwtException ex,
                                          HttpServletResponse response,
                                          @Nullable Object handler) throws IOException {
        response.sendError(SC_UNAUTHORIZED, ex.getMessage());
        return new ModelAndView();
    }

    private ModelAndView handleException(Exception ex,
                                         HttpServletResponse response,
                                         @Nullable Object handler) throws IOException {
        response.sendError(SC_BAD_REQUEST, ex.getMessage());
        return new ModelAndView();
    }
}
