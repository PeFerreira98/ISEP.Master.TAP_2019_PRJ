package controller

import org.scalatest.FunSuite
import controller.ScheduleController._
import domain._


class ScheduleControllerTest extends FunSuite {

  val ln = List(Nurse("Antonia", Set(RoleA)), Nurse("Manuela", Set(RoleB, RoleC)), Nurse("João", Set(RoleA, RoleC)))
  val ls = List(NurseRequirement(1, RoleA), NurseRequirement(1, RoleB), NurseRequirement(1, RoleC))

  test("testNursesForSchedule") {
    // Positive
    assert(nursesForSchedule(ln, NurseRequirement(1, RoleA)).isDefined)
    assert(nursesForSchedule(ln, NurseRequirement(1, RoleB)).isDefined)
    assert(nursesForSchedule(ln, NurseRequirement(1, RoleC)).isDefined)
    assert(nursesForSchedule(ln, NurseRequirement(2, RoleA)).isDefined)
    assert(nursesForSchedule(ln, NurseRequirement(2, RoleC)).isDefined)

    // Negative
    assert(!nursesForSchedule(ln, NurseRequirement(1, RoleD)).isDefined)
    assert(!nursesForSchedule(ln, NurseRequirement(2, RoleB)).isDefined)
  }

  test("testScheduler") {
    // Positive
    assert(scheduler(ln, List(NurseRequirement(1, RoleA))).isDefined)
    assert(scheduler(ln, List(NurseRequirement(1, RoleA), NurseRequirement(1, RoleB))).isDefined)
    assert(scheduler(ln, List(NurseRequirement(1, RoleA), NurseRequirement(1, RoleB), NurseRequirement(1, RoleC))).isDefined)
    assert(scheduler(ln, List(NurseRequirement(2, RoleA), NurseRequirement(1, RoleB))).isDefined)
    assert(scheduler(ln, List(NurseRequirement(1, RoleA), NurseRequirement(2, RoleC))).isDefined)
    // Negative

    assert(!scheduler(ln, List(NurseRequirement(1, RoleD))).isDefined)
    assert(!scheduler(ln, List(NurseRequirement(1, RoleA), NurseRequirement(2, RoleB))).isDefined)
    assert(!scheduler(ln, List(NurseRequirement(1, RoleA), NurseRequirement(1, RoleB), NurseRequirement(1, RoleD))).isDefined)
    assert(!scheduler(ln, List(NurseRequirement(3, RoleA), NurseRequirement(1, RoleB))).isDefined)
    assert(!scheduler(ln, List(NurseRequirement(1, RoleB), NurseRequirement(3, RoleC))).isDefined)
    assert(!scheduler(ln, List(NurseRequirement(1, RoleB), NurseRequirement(2, RoleC))).isDefined)
  }
}
