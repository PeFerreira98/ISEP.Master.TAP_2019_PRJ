package io

import domain.{Aircraft, Runway}

object XmlAirportReader {

  val loadAirport : String => (Set[Aircraft], Set[Runway]) = loadAirportPriv
  val loadAircrafts : String => Set[Aircraft] = loadAircraftsPriv
  val loadRunways : String => Set[Runway] = loadRunwaysPriv

  def loadAirportPriv(filePath: String) : (Set[Aircraft], Set[Runway]) = {
    (loadAircrafts(filePath), loadRunways(filePath))
  }

  private def loadAircraftsPriv(filePath: String) : Set[Aircraft] = {
    // TODO: DO IT!
    ???
  }

  private def loadRunwaysPriv(filePath: String) : Set[Runway] = {
    // TODO: DO IT!
    ???
  }
}
