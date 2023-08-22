package me.atour.thift.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.atour.thriftjar.ThriftCompiler;

public class Thrift {

  private final File thriftExecutable;
  private final String[] arguments;

  private Thrift(File executable, String[] args) {
    thriftExecutable = executable;
    arguments = args;
  }

  public void compile() throws FailedToRunThriftCompilerException {
    try {
      ThriftCompiler.run(thriftExecutable, arguments);
    } catch (IOException | InterruptedException e) {
      throw new FailedToRunThriftCompilerException(e);
    }
  }

  public static Builder builder(File executable, File out) {
    return new Builder(executable, out);
  }

  public static class Builder {
    private boolean strict = false;
    private boolean verbose = false;
    private boolean recurse = false;
    private boolean allowNegKeys = false;
    private String gen = "java";
    private final File outputFolder;
    private final File thriftExecutable;

    public Builder(File executable, File out) {
      outputFolder = out;
      thriftExecutable = executable;
    }

    public Builder strict(boolean value) {
      strict = value;
      return this;
    }

    public Builder verbose(boolean value) {
      verbose = value;
      return this;
    }

    public Builder recurse(boolean value) {
      recurse = value;
      return this;
    }

    public Builder allowNegKeys(boolean value) {
      allowNegKeys = value;
      return this;
    }

    public Builder gen(String language) {
      gen = language;
      return this;
    }

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
