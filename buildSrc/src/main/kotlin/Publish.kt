import org.gradle.api.publish.maven.MavenPom


/**
 * Adds required information to the published `pom.xml` file.
 */
fun MavenPom.withXml(
    name: String,
    description: String
) = withXml {
  asNode().apply {
    appendNode("name", name)
    appendNode("description", description)
    appendNode("url", Github.pomUrl)

    appendNode("licenses").appendNode("license").apply {
      appendNode("name", Github.pomLicenseName)
      appendNode("url", Github.pomLicenseUrl)
      appendNode("distribution", Github.pomLicenseDist)
    }
    appendNode("developers").appendNode("developer").apply {
      appendNode("id", "jangalinski")
      appendNode("name", "Jan Galinski")
    }
    appendNode("scm").apply {
      appendNode("url", Github.pomScmUrl)
      appendNode("connection", Github.pomScmConnection)
    }
  }
}
