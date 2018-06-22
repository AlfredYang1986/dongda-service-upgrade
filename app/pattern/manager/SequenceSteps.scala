package pattern.manager

import com.pharbers.model.detail.commonresult
import model.steps.{commonstep}

//case class SequenceSteps(steps : List[commonstep], cr : Option[commonresult], er : Option[commonerror])
case class SequenceSteps(steps : List[commonstep], cr : Option[commonresult])
