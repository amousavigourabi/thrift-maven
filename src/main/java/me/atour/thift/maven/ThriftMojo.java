package me.atour.thift.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.atour.thriftjar.ThriftCompiler;
import me.atour.thriftjar.ThriftExtractor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * The Thrift compile mojo.
 */
@Mojo(name = "compile", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class ThriftMojo extends AbstractMojo {

  /**
   * The Thrift {@code allowNegKeys} flag.
   */
  @Parameter(property = "thrift.allowNegativeKeys", defaultValue = "false", required = true)
  private boolean allowNegativeKeys;

  /**
   * The Thrift {@code strict} flag.
   */
  @Parameter(property = "thrift.strict", defaultValue = "false", required = true)
  private boolean strict;

  /**
   * The Thrift {@code verbose} flag.
   */
  @Parameter(property = "thrift.verbose", defaultValue = "false", required = true)
  private boolean verbose;

  /**
   * The Thrift {@code recurse} flag.
   */
  @Parameter(property = "thrift.recurse", defaultValue = "true", required = true)
  private boolean recurse;

  /**
   * The source directory of the files to compile.
   */
  @Parameter(
      property = "thrift.sourceDirectory",
      defaultValue = "${project.basedir}/src/main/thrift",
      required = true)
  private File sourceDirectory;

  /**
   * The source directory of the files to compile.
   */
  @Parameter(property = "thrift.targetDirectory", required = true)
  private File targetDirectory;

  /**
   * Version of Thrift to use to compile the files.
   */
  @Parameter(property = "thrift.version", required = true)
  private String version;

  /**
   * Thrift language.
   */
  @Parameter(property = "thrift.language", defaultValue = "java", required = true)
  private String language;

  /**
   * Executes the {@code thrift:compile} goal.
   * Compiles all {@code *.thrift} files in the specified source directory.
   *
   * @throws MojoExecutionException failed to find Thrift
   * @throws MojoFailureException failed to compile
   */
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
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
    if (allowNegativeKeys) {
      arguments.add("-allowNegKeys");
    }
    arguments.add("--gen");
    arguments.add(language);
    try {
      File thriftExecutable = ThriftExtractor.extract(version);
      ThriftCompiler.run(thriftExecutable, arguments.toArray(String[]::new));
    } catch (IOException | InterruptedException e) {
      throw new MojoExecutionException(e);
    } catch (Exception e) {
      throw new MojoFailureException(e);
    }
  }
}
