/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.expression;

/**
 * 
 * @author chaostone
 * @version $Id: EvaluationException.java Mar 5, 2012 12:13:41 AM chaostone $
 */
public class EvaluationException extends RuntimeException{

	private static final long serialVersionUID = 7366966661039007890L;

	public EvaluationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs the exception using a message and cause.
     *
     * @param message  the message to use
     * @param cause  the underlying cause
     */
    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
