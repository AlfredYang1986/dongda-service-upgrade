package pattern.manager

import model.steps.commonstep
import com.pharbers.model.detail.commonresult

case class SequenceSteps(steps : List[commonstep], cr : Option[commonresult])
