package com.xiaoyuen.ethcompose.scan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.util.ToastUtil


class ScanCodeActivity : AppCompatActivity(), QRCodeView.Delegate {

    private var mQRCodeView: ZXingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)
        mQRCodeView = findViewById(R.id.mQRCodeView)
        mQRCodeView!!.setDelegate(this)

        findViewById<ImageView>(R.id.mClose).setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mQRCodeView!!.startCamera();//打开相机
        mQRCodeView!!.showScanRect();//显示扫描框
        mQRCodeView!!.startSpot();//开始识别二维码
    }

    override fun onScanQRCodeSuccess(result: String?) {
        mQRCodeView!!.startSpot();
        val intent = Intent()
        intent.putExtra("result", result)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {
        ToastUtil.show(this, "错误")
    }

    override fun onStop() {
        mQRCodeView!!.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        mQRCodeView!!.onDestroy()
        super.onDestroy()
    }

}