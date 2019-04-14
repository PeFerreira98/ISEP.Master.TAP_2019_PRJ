package domain

// Entity representing one sentiword word entry
case class Entry (word: String, positiveScore: Double, negativeScore: Double)