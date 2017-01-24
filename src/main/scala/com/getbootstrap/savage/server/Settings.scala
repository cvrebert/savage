package com.getbootstrap.savage.server

import scala.concurrent.duration.FiniteDuration
import scala.collection.JavaConverters._
import com.typesafe.config.Config
import akka.actor.ActorSystem
import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import akka.util.ByteString
import org.eclipse.egit.github.core.RepositoryId
import com.getbootstrap.savage.crypto.RsaPublicKey
import com.getbootstrap.savage.github.Branch
import com.getbootstrap.savage.util.{FilePathWhitelist,FilePathWatchlist,Utf8String,RichConfig}

class SettingsImpl(config: Config) extends Extension {
  val MainRepoId: RepositoryId = RepositoryId.createFromId(config.getString("savage.github-repo-to-watch"))
  val TestRepoId: RepositoryId = RepositoryId.createFromId(config.getString("savage.github-test-repo"))
  val BotUsername: String = config.getString("savage.username")
  val BotPassword: String = config.getString("savage.password")
  val GitHubWebHookSecretKey: ByteString = ByteString(config.getString("savage.github-web-hook-secret-key").utf8Bytes)
  val TravisPublicKey: RsaPublicKey = RsaPublicKey.fromPem(config.getString("savage.travis-public-key")).get
  val UserAgent: String = config.getString("spray.can.client.user-agent-header")
  val DefaultPort: Int = config.getInt("savage.default-port")
  val SquelchInvalidHttpLogging: Boolean = config.getBoolean("savage.squelch-invalid-http-logging")
  val Whitelist: FilePathWhitelist = new FilePathWhitelist(config.getStringList("savage.whitelist").asScala)
  val Watchlist: FilePathWatchlist = new FilePathWatchlist(config.getStringList("savage.file-watchlist").asScala)
  val BranchPrefix: String = config.getString("savage.branch-prefix")
  val AllowedBaseBranches: Set[Branch] = config.getStringList("savage.allowed-base-branches").asScala.flatMap{ Branch(_) }.toSet
  val TrustedOrganizations: Set[String] = config.getStringList("savage.trusted-orgs").asScala.toSet
  val SetCommitStatus: Boolean = config.getBoolean("savage.set-commit-status")
  val TravisTimeout: FiniteDuration = config.getFiniteDuration("savage.travis-timeout")
  val ShowPreviewUrls: Boolean = config.getBoolean("savage.show-preview-urls")
}
object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {
  override def lookup() = Settings
  override def createExtension(system: ExtendedActorSystem) = new SettingsImpl(system.settings.config)
  override def get(system: ActorSystem): SettingsImpl = super.get(system)
}
