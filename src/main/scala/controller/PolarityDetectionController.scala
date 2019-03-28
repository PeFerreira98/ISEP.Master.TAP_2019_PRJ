package controller


/*
import domain.{Entry}

object PolarityDetectionController {

  // the scheduler
  val detectPolarity: (List[Nurse], List[NurseRequirement]) => Option[List[Shift]] = naiveScheduler

  // Returns the a list of nurses which fulfills the requirement
  def nursesForSchedule(ln: List[Nurse], s: NurseRequirement): Option[List[Nurse]] = {
    val lns = ln.filter(_.roles.contains(s.role)).take(s.number)
    if (lns.size == s.number) Some(lns) else None
  }

  // Schedules in a naive way
  private def naiveScheduler(ln: List[Nurse], ls: List[NurseRequirement]): Option[List[Shift]] = {
    val ll = ls.foldLeft[Option[(List[Shift], List[Nurse])]](Some(List(), ln)) {
      case (ss, s) =>
        // scala for-yield format (syntactic sugar)
        for (
          (ls, ln) <- ss;
          lns <- nursesForSchedule(ln, s)
        ) yield (lns.map(n => Shift(n.name, s.role)), ln.diff(lns))
    }
    ll.map { case (ls, _) => ls }
  }
}
*/