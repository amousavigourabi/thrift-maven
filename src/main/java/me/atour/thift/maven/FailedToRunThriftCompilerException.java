package me.atour.thift.maven;

/**
 * Thrown internally when the Thrift compiler fails on an exception.
 * Used for {@link InterruptedException}s and {@link java.io.IOException}s.
 */
public class FailedToRunThriftCompilerException extends Exception {

  /**
   * Sets up the exception with a cause.
   *
   * @param cause the cause of this exception
   */
  public FailedToRunThriftCompilerException(Throwable cause) {
    super(cause);
  }
}
