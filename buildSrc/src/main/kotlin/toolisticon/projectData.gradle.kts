package toolisticon

object ProjectDataExtensions {
  val key = "toolisticonProjectData"
  val UNDEF = "<UNDEFINED>"

  val Project.toolisticonProjectData get() = this.extra[key] as ProjectData
}


data class GithubData(
  val token: String,
  val organization: String,
  val repository: String,
  val description: String
) {
  val path = "$organization/$repository"
  val readme = "README.md"

  val url = "https://github.com/$path"
  val issues = "$url/issues"
  val scm = "scm:git:git://github.com/$path"
}

data class PomData(
  val groupId: String,
  val artifactId: String,
  val version: String,
  val description:String
)

data class VersionData(val value:String) {
  companion object {
    const val SNAPSHOT_SUFFIX = "-SNAPSHOT"
  }

  val isSnapShot = value.endsWith(SNAPSHOT_SUFFIX)
}

data class BintrayData(val user: String, val key: String)

data class ProjectData(
  val pom: PomData,
  val github: GithubData,
  val bintray: BintrayData,
  val version: VersionData
)

fun EnvironmentPropertyEnum.read(): String {
  fun read(access: (String) -> String?) = this.keys.map { access(it) }
    .filterNotNull()
    .firstOrNull()

  return read { System.getenv(it) }
    ?: read { properties[it] as String? }
    ?: ProjectDataExtensions.UNDEF
}



/**
 * stores the resolved ProjectData on project.extra.
 * It can be accessed anywhere using
 *
 * `val projectData = project.extra['toolisticonProjectData']
 */
project.extra[ProjectDataExtensions.key] = ProjectData(
  github = GithubData(
    token = EnvironmentPropertyEnum.GITHUB_TOKEN.read(),
    organization = stringProperty("github.organization"),
    repository = rootProject.name,
    description = EnvironmentPropertyEnum.GITHUB_DESCRIPTION.read()
  ),
  bintray = BintrayData(
    user = EnvironmentPropertyEnum.BINTRAY_USER.read(),
    key = EnvironmentPropertyEnum.BINTRAY_KEY.read()
  ),
  pom = PomData(
    groupId = EnvironmentPropertyEnum.POM_GROUPID.read(),
    artifactId = project.name,
    version = EnvironmentPropertyEnum.POM_VERSION.read(),
    description = project.description ?: ""
  ),
  version = VersionData(value = EnvironmentPropertyEnum.POM_VERSION.read())
)

tasks {
  register(ProjectDataExtensions.key) {
    group = "toolisticon"
    description = "print out all projectData for module"

    val projectData = project.extra[ProjectDataExtensions.key] as ProjectData
    doLast {
      println("""
        Module: ${project.name}
        $projectData
      """.trimIndent())
    }
  }
}



enum class EnvironmentPropertyEnum {
  POM_GROUPID,
  POM_VERSION,
  GITHUB_TOKEN,
  GITHUB_ORGANIZATION,
  GITHUB_REPOSITORY,
  GITHUB_DESCRIPTION,
  BINTRAY_USER,
  BINTRAY_KEY;

  val keys = listOf(
    name,
    name.toLowerCase().replace("_", "."),
    name.toLowerCase().replace(regex = Regex("_([a-z])")) { it.groupValues[1].toUpperCase() }
  )
}

fun stringProperty(key: String) = properties[key] as String
