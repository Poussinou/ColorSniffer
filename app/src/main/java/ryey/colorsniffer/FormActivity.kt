package ryey.colorsniffer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FormActivity : AppCompatActivity(), FormFragment.ResultListener {

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FormFragment) {
            fragment.resultListener = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
    }

    override fun onCompleted(coloringResult: ColoringResult) {
        Toast.makeText(this, "Form completed with coloring method <%s>".format(coloringResult.coloringMethod), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.putExtras(coloringResult.asIPCBundle())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCancelled() {
        Toast.makeText(this, "Form cancelled", Toast.LENGTH_LONG).show()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
