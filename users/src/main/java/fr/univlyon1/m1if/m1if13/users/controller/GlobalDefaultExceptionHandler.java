
package fr.univlyon1.m1if.m1if13.users.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Controller for handling exception.
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {
    /**
     * The constant DEFAULT_ERROR_VIEW.
     */
    public static final String DEFAULT_ERROR_VIEW = "error";

    private ModelAndView generateErrorView(final HttpServletRequest req,
                              final Exception exception,
                              final HttpStatus status) {
        System.out.println("Request: " + req.getRequestURI() + " raised " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", status);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    /**
     * Handle bad request model and view.
     *
     * @param req       the req
     * @param exception the exception
     * @return the model and view
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ModelAndView handleBadRequest(final HttpServletRequest req,
                                       final BadRequestException exception) {
        return generateErrorView(req, exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle not authorized model and view.
     *
     * @param req       the req
     * @param exception the exception
     * @return the model and view
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleNotAuthorized(final HttpServletRequest req,
                                       final AuthenticationException exception) {
        return generateErrorView(req, exception, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle not found model and view.
     *
     * @param req       the req
     * @param exception the exception
     * @return the model and view
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNotFound(final HttpServletRequest req,
                                       final NoSuchElementException exception) {
        return generateErrorView(req, exception, HttpStatus.NOT_FOUND);
    }

    /**
     * Demonstrates how to take total control - set up a model, add useful
     * information and return the "support" view name. This method explicitly
     * creates and returns
     *
     * @param req       Current HTTP request.
     * @param exception The exception thrown - always {@link Exception}.
     * @return The model and view used by the DispatcherServlet to generate output.
     * @throws Exception the exception
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(final HttpServletRequest req, final Exception exception)
            throws Exception {

        // Rethrow annotated exceptions or they will be processed here instead.
        if (AnnotationUtils.findAnnotation(exception.getClass(),
                ResponseStatus.class) != null) {
            throw exception;
        }
        return generateErrorView(req, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
