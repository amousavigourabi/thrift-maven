package me.atour.thift.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.atour.thriftjar.ThriftCompiler;

/**
 * Wrapper for Thrift.
 */
public class Thrift {

  private final File thriftExecutable;
  private final String[] arguments;

  /**
   * Sets up a Thrift run.
   *
   * @param executable the Thrift executable to run
   * @param args the arguments to pass to the executable
   */
  private Thrift(File executable, String[] args) {
    thriftExecutable = executable;
    arguments = args;
  }

  /**
   * Compile using Thrift.
   *
   * @throws FailedToRunThriftCompilerException when the compiler invocation fails
   */
  public void compile() throws FailedToRunThriftCompilerException {
    try {
      ThriftCompiler.run(thriftExecutable, arguments);
    } catch (IOException | InterruptedException e) {
      throw new FailedToRunThriftCompilerException(e);
    }
  }

  /**
   * Sets up a builder for Thrift.
   *
   * @param executable the location of the Thrift executable
   * @param out the target folder
   * @return a Thrift builder
   */
  public static Builder builder(File executable, File out) {
    return new Builder(executable, out);
  }

  /**
   * Builder for Thrift.
   */
  public static class Builder {
    private boolean strict = false;
    private boolean verbose = false;
    private boolean recurse = false;
    private boolean allowNegKeys = false;
    private String gen = "java";
    private final File outputFolder;
    private final File thriftExecutable;

    /**
     * Constructs the builder.
     *
     * @param executable the location of the executable
     * @param out the target output location
     */
    public Builder(File executable, File out) {
      outputFolder = out;
      thriftExecutable = executable;
    }

    /**
     * Sets the strict flag for the run.
     *
     * @param value whether to set the strict flag
     * @return this builder
     */
    public Builder strict(boolean value) {
      strict = value;
      return this;
    }

    /**
     * Sets the verbose flag for the run.
     *
     * @param value whether to set the verbose flag
     * @return this builder
     */
    public Builder verbose(boolean value) {
      verbose = value;
      return this;
    }

    /**
     * Sets the recurse flag for the run.
     *
     * @param value whether to set the recurse flag
     * @return this builder
     */
    public Builder recurse(boolean value) {
      recurse = value;
      return this;
    }

    /**
     * Sets the allow-neg-keys flag for the run.
     *
     * @param value whether to set the allow-neg-keys flag
     * @return this builder
     */
    public Builder allowNegKeys(boolean value) {
      allowNegKeys = value;
      return this;
    }

    /**
     * Sets the --gen option for the run.
     *
     * @param language the value to set for --gen, such as java
     * @return this builder
     */
    public Builder gen(String language) {
      gen = language;
      return this;
    }

    /**
     * Build Thrift.
     *
     * @return a Thrift instance constructed from this builder
     */
    public Thrift build() {
      List<String> arguments = new ArrayList<>();
      if (strict) {
        arguments.add("-strict");
      }
      if (verbose) {
        arguments.add("-verbose");
      }
      if (recurse) {
        arguments.add("-recurse");
      }
      if (allowNegKeys) {
        arguments.add("--allow-neg-keys");
      }
      arguments.add("--gen");
      arguments.add(gen);
      arguments.add("-out");
      arguments.add(outputFolder.getAbsolutePath());
      return new Thrift(thriftExecutable, arguments.toArray(String[]::new));
    }
  }
}
