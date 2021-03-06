package ryey.colorsniffer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener

class FormFragment : Fragment(R.layout.fragment_form), StepperFormListener {

    private lateinit var defaultColorStep: DefaultColorStep
    private lateinit var coloringMethodStep: ColoringMethodStep
    private lateinit var previewStep: PreviewStep

    var resultListener: ResultListener? = null
    var initialDataSource: InitialDataSource? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        defaultColorStep = DefaultColorStep(resources)
        coloringMethodStep = ColoringMethodStep(resources)
        previewStep = PreviewStep(resources, defaultColorStep, coloringMethodStep)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container)

        // Find the form view, set it up and initialize it.
        val verticalStepperForm = view.findViewById<VerticalStepperFormView>(R.id.stepper_form)
        verticalStepperForm
            .setup(this, defaultColorStep, coloringMethodStep, previewStep)
            .includeConfirmationStep(false)
            .displayCancelButtonInLastStep(true)
            .init()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialDataSource?.get()?.run {
            defaultColor?.let {
                defaultColorStep.defaultColorHelper.defaultColor = it
            }
//            TODO: the following
//            color?.let {
//
//            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCompletedForm() {
        resultListener?.onCompleted(previewStep.previewViewHelper.getColoringResult())
    }

    override fun onCancelledForm() {
        resultListener?.onCancelled()
    }

    interface ResultListener {
        fun onCompleted(coloringResult: ColoringResult)
        fun onCancelled()
    }

    interface InitialDataSource {
        fun get(): ColoringResult.Partial?
    }

}