package me.atour.thift.maven;

import java.io.File;
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
  @Parameter(property = "thrift.allowNegativeKeys", defaultValue = "false")
  private boolean allowNegativeKeys;

  /**
   * The Thrift {@code strict} flag.
   */
  @Parameter(property = "thrift.strict", defaultValue = "false")
  private boolean strict;

  /**
   * The Thrift {@code verbose} flag.
   */
  @Parameter(property = "thrift.verbose", defaultValue = "false")
  private boolean verbose;

  /**
   * The Thrift {@code recurse} flag.
   */
  @Parameter(property = "thrift.recurse", defaultValue = "true")
  private boolean recurse;

  /**
   * The source directory of the files to compile.
   */
  @Parameter(property = "thrift.sourceDirectory", defaultValue = "${project.basedir}/src/main/thrift")
  private File sourceDirectory;

  /**
   * The source directory of the files to compile.
   */
  @Parameter(
      property = "thrift.targetDirectory",
      defaultValue = "${project.build.directory}/generated-sources/thrift")
  private File targetDirectory;

  /**
   * Version of Thrift to use to compile the files.
   */
  @Parameter(property = "thrift.version", defaultValue = "latest")
  private String version;

  /**
   * Thrift language.
   */
  @Parameter(property = "thrift.language", defaultValue = "java")
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
    try {
      File thriftExecutable = ThriftExtractor.extract(version);
      Thrift thriftCompiler = Thrift.builder(thriftExecutable, targetDirectory)
          .strict(strict)
          .allowNegKeys(allowNegativeKeys)
          .gen(language)
          .recurse(recurse)
          .verbose(verbose)
          .build();
      thriftCompiler.compile();
    } catch (FailedToRunThriftCompilerException e) {
      throw new MojoFailureException(e);
    } catch (Exception e) {
      throw new MojoExecutionException(e);
    }
  }
}
