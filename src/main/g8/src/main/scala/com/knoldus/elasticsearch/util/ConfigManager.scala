package com.knoldus.elasticsearch.util

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Load the config file once and make it available for the micro service.
  */
object ConfigManager {
  lazy val config: Config = ConfigFactory.load()
}
