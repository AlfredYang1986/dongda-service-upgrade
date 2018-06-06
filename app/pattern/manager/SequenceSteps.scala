package pattern.manager

import model.steps.{commonerror, commonresult, commonstep}

//case class SequenceSteps(steps : List[commonstep], cr : Option[commonresult], er : Option[commonerror])
case class SequenceSteps(steps : List[commonstep], cr : Option[commonresult])
