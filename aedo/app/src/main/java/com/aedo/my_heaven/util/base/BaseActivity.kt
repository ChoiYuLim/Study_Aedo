package com.aedo.my_heaven.util.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.aedo.my_heaven.R
import com.aedo.my_heaven.util.`object`.ActivityControlManager
import com.aedo.my_heaven.util.alert.AlertDialogManager
import com.aedo.my_heaven.util.alert.LoadingDialog
import com.aedo.my_heaven.util.common.CommonData
import com.aedo.my_heaven.util.log.LLog.TAG
import com.aedo.my_heaven.util.log.LLog.e
import com.aedo.my_heaven.view.faq.FAQActivity
import com.aedo.my_heaven.view.login.LoginActivity
import com.aedo.my_heaven.view.main.MainActivity
import com.aedo.my_heaven.view.main.PushActivity
import com.aedo.my_heaven.view.main.SideMenuActivity
import com.aedo.my_heaven.view.main.detail.center.CenterActivity
import com.aedo.my_heaven.view.main.detail.make.MakeActivity
import com.aedo.my_heaven.view.main.detail.modify.ModifyActivity
import com.aedo.my_heaven.view.main.detail.search.SearchActivity
import com.aedo.my_heaven.view.main.detail.send.SendActivity
import com.aedo.my_heaven.view.main.detail.shop.MyOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.ShopActivity
import com.aedo.my_heaven.view.main.detail.shop.ShopTermActivity
import com.aedo.my_heaven.view.notice.NoticeActivity
import com.aedo.my_heaven.view.notice.NoticeDetailActivity
import com.aedo.my_heaven.view.side.ThanksActivity
import com.aedo.my_heaven.view.side.coun.CounselingActivity
import com.aedo.my_heaven.view.side.coun.UploadCounselingActivity
import com.aedo.my_heaven.view.side.guide.GuideActivity
import com.aedo.my_heaven.view.side.information.InforMationActivity
import com.aedo.my_heaven.view.side.list.ListActivity
import com.aedo.my_heaven.view.side.list.detail.MessageActivity
import com.aedo.my_heaven.view.side.list.detail.MessageUploadActivity
import com.aedo.my_heaven.view.side.list.detail.WaringActivity
import com.aedo.my_heaven.view.side.setting.SettingActivity
import com.aedo.my_heaven.view.term.TermActivity
import com.iamport.sdk.domain.utils.Util
import io.realm.Realm
import kotlinx.android.synthetic.main.one_button_dialog.view.*
import kotlinx.android.synthetic.main.two_button_dialog.view.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

open class BaseActivity : AppCompatActivity() {
    internal open var instance: BaseActivity? = null
    var ResultView: ActivityResultLauncher<Intent>? = null
    var comm: CommonData? = CommonData().getInstance()
    var alert: AlertDialogManager? = null

    internal var dialog: Dialog? = null

    internal val realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        instance = this
        alert = AlertDialogManager(this)
        dialog = LoadingDialog(this)
        val window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    // StatusBar 색상 조정
    internal fun inStatusBar() {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = getColor(R.color.progress)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onResume() {
        super.onResume()
        if (Util.isInternetAvailable(this)) {
            Log.d(TAG, "네트워크 연결중")
        } else {
            networkDialog()
            return
        }
    }

    open fun onTitleLeftClick(v: View?) {}

    open fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    open fun moveAndFinishActivity(activity: Class<*>?) {
        ActivityControlManager.moveAndFinishActivity(this, activity)
    }

    open fun hideSoftKeyboard(v: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    open fun hideSoftKeyboard() {
        val v = currentFocus
        if (v != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    val hash: String?
        get() {
            val context = applicationContext
            val pm = context.packageManager
            val packageName = context.packageName
            var cert: String? = null
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    @SuppressLint("WrongConstant") val packageInfo = packageManager.getPackageInfo(
                        getPackageName(),
                        PackageManager.GET_SIGNING_CERTIFICATES
                    )
                    val signatures = packageInfo.signingInfo.apkContentsSigners
                    val md = MessageDigest.getInstance("SHA1")
                    for (signature in signatures) {
                        md.update(signature.toByteArray())
                        cert = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                        cert = cert.replace(
                            Objects.requireNonNull(System.getProperty("line.separator")).toRegex(),
                            ""
                        )
                    }
                } else {
                    @SuppressLint("PackageManagerGetSignatures") val packageInfo =
                        pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                    val certSignature = packageInfo.signatures[0]
                    val msgDigest = MessageDigest.getInstance("SHA1")
                    msgDigest.update(certSignature.toByteArray())
                    cert = Base64.encodeToString(msgDigest.digest(), Base64.DEFAULT)
                    cert = cert.replace(
                        Objects.requireNonNull(System.getProperty("line.separator")).toRegex(), ""
                    )
                    Log.i("test", "Cert=$cert")
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e("예외발생")
            } catch (e: NoSuchAlgorithmException) {
                e("예외발생")
            }
            return cert
        }

    @SuppressLint("ResourceType")
    internal fun serverDialog() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.server_check)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }

    }

    internal fun networkDialog() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.network_check)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
    }

    internal fun rootingDialog() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.warning_rooting)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
    }

    internal fun smscheck() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.auth_num_wrong_text)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            dialog.dismiss()
        }
    }

    internal fun termcheck() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.term_check)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            dialog.dismiss()
        }
    }

    internal fun phonecheck() {
        val myLayout = layoutInflater.inflate(R.layout.one_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_one)
        textView.text = getString(R.string.phone_check)
        val dialog = build.create()
        dialog.show()

        myLayout.ok_btn.setOnClickListener {
            dialog.dismiss()
        }
    }

    internal fun UpdateDialog() {
        val myLayout = layoutInflater.inflate(R.layout.two_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_second)
        textView.text = getString(R.string.update_check)
        val dialog = build.create()
        dialog.show()

        myLayout.finish_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        myLayout.update_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
    }

    internal fun listdelete() {
        val myLayout = layoutInflater.inflate(R.layout.two_button_dialog, null)
        val build = AlertDialog.Builder(this).apply {
            setView(myLayout)
        }
        val textView: TextView = myLayout.findViewById(R.id.popTv_second)
        textView.text = getString(R.string.list_delete)
        val dialog = build.create()
        dialog.show()

        myLayout.finish_btn.text = getString(R.string.btn_delete)
        myLayout.update_btn.text = getString(R.string.btn_modify)

        myLayout.finish_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        myLayout.update_btn.setOnClickListener {
            finish()
            dialog.dismiss()
        }
    }

    internal fun moveSide() {
        val intent = Intent(this, SideMenuActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveCenter() {
        val intent = Intent(this, CenterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveMake() {
        val intent = Intent(this, MakeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveSend() {
        val intent = Intent(this, SendActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveRE() {
        val intent = Intent(this, ModifyActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveShop() {
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveList() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveLogins() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveWaring() {
        val intent = Intent(this, WaringActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }


    internal fun moveMessage() {
        val intent = Intent(this, MessageActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveMessageUpload() {
        val intent = Intent(this, MessageUploadActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveSetting() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveInfor() {
        val intent = Intent(this, InforMationActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        dialog?.dismiss()
        finish()
    }

    internal fun moveCounseling() {
        val intent = Intent(this, CounselingActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveUploadCoun() {
        val intent = Intent(this, UploadCounselingActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun movePush() {
        val intent = Intent(this, PushActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveNotice() {
        val intent = Intent(this, NoticeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveNoticeDetail() {
        val intent = Intent(this, NoticeDetailActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveThanks() {
        val intent = Intent(this, ThanksActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveFAQ() {
        val intent = Intent(this, FAQActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveTerms() {
        val intent = Intent(this, TermActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveShopTerm() {
        val intent = Intent(this, ShopTermActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveGuide() {
        val intent = Intent(this, GuideActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    internal fun moveMyOrder() {
        val intent = Intent(this, MyOrderActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }


}