package me.atour.thift.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "compile", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution =
    ResolutionScope.COMPILE, threadSafe = true)
public class ThriftMojo extends AbstractMojo {

  @Parameter(property = "thrift.strict", defaultValue = "false")
  private boolean strict;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

  }
}
