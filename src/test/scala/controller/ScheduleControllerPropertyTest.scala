package controller

import domain._
import org.scalacheck.{Gen, Properties, Test}
import org.scalacheck.Prop.forAll

object ScheduleControllerPropertyTest extends Properties("ScheduleController") {

  // TODO: Create a Nurse generator
  // TODO: the names must have 5 characters
  // TODO: the roles must be a non empty container of the available roles
  val gNurse: Gen[Nurse] =
    ???

  // Create a sized List of Nurse generator
  // Use the nurse generator gNurse
  // The Generated list MUST have a maximum of 5 nurses
  val glNurses: Gen[List[Nurse]] = Gen.sized {
    s => Gen.listOfN(math.min(5, s), gNurse)
  }

  // Create a List of Nurse requirements
  // This function receives a list of nurses and creates a List of NurseRequirement
  // using the first role of each nurse in the list
  def createNurseRequirements(ln: List[Nurse]): List[NurseRequirement] = {
    ln.map { n => NurseRequirement(1, n.roles.head) }
  }

  // Create a List of Nurse requirements
  // This function receives a list of nurses and creates a List of NurseRequirement
  // using the first role of each nurse in the list
  // it also aggregates the results
  def createAggregatedNurseRequirements(ln: List[Nurse]): List[NurseRequirement] = {
    ln.map(n => (1, n.roles.head)).groupBy(_._2).map { case (r, l) => NurseRequirement(l.size, r) }.toList
  }

  // TODO: Generate a List of Nurse requirements
  // TODO: This function receives a list of nurses and creates a List of NurseRequirement
  // TODO: then it generates several lists using the permutations of the NurseRequirements
  def genNurseRequirement(ln: List[Nurse])(fns: List[Nurse] => List[NurseRequirement]): Gen[List[NurseRequirement]] =
    ???


  // Allows parameter redefinition. Minimum successful tests, for instance
  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(500)

  // checks the property for the generated lists of nurses and the respective requirements
  property("requirements from nurses simple") =
    forAll(glNurses)(ln => {
        ScheduleController.scheduler(ln, createNurseRequirements(ln)).isDefined
    })


  // TODO: checks the property for the generated lists of nurses and the respective requirements
  // TODO: the requirements are permutated to increase the coverage
  property("requirements from nurses permuted") =
    ???

  // TODO: checks the property for the generated lists of nurses and the respective requirements agregated
  // TODO: afterwards, the requirements are permutated to increase the coverage
  property("requirements from nurses permuted and aggregated") =
    ???

}
