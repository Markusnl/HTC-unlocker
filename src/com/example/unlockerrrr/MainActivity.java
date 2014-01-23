package com.example.unlockerrrr;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.htc.circontrol.CIRControl;
import com.htc.htcircontrol.HtcIrData;

public class MainActivity extends Activity {

	private final static String TAG = "OneCIR";
	public static final int TEST_COUNT = 1;
	private int run = 0;

	CIRControl mControl;
	int freq;
	boolean poc = false;
	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11,
			btn12, btn13, btn14, btn15, btn16, btn17;
	TextView text1;
	ArrayList<UUID> mRIDs = new ArrayList<UUID>();
	HtcIrData mLearntKey;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			UUID rid;
			switch (msg.what) {
			case CIRControl.MSG_RET_LEARN_IR:
				// CIR APP developer can check UUID to see if the reply message
				// is what he/she is interesting.
				rid = (UUID) msg.getData().getSerializable(
						CIRControl.KEY_RESULT_ID);
				Log.e(TAG, "Receive IR Returned UUID: " + rid);

				// CIR APP developer must check learning IR data.
				// The learning IR data is in HtcIrData object.
				// If data is null, the learning is not successful, so developer
				// can check error type.
				mLearntKey = (HtcIrData) msg.getData().getSerializable(
						CIRControl.KEY_CMD_RESULT);
				if (mLearntKey != null) {
					text1.setText("Repeat:" + mLearntKey.getRepeatCount()
							+ " Freq:" + mLearntKey.getFrequency() + " Frame:"
							+ mLearntKey.getFrame().length);
				} else {
					text1.setText("Learn IR error=" + msg.arg1);
					switch (msg.arg1) {
					case CIRControl.ERR_LEARNING_TIMEOUT:
						// TODO: timeout error because of CIR do not receive IR
						// data from plastic remote.
						break;
					case CIRControl.ERR_PULSE_ERROR:
						// TODO:
						// CIR receives IR data from plastic remote, but data is
						// inappropriate.
						// The common error is caused by user he/she does not
						// align the phone's CIR receiver
						// with CIR transmitter of plastic remote.
						break;
					case CIRControl.ERR_OUT_OF_FREQ:
						// TODO:
						// This error is to warn user that the plastic remote is
						// not supported or
						// the phone's CIR receiver does not align with CIR
						// transmitter of plastic remote.
						break;
					case CIRControl.ERR_IO_ERROR:
						// TODO:
						// CIR hardware component is busy in doing early CIR
						// activity.
						break;
					default:
						// TODO: other errors
						break;
					}
				}
				break;
			case CIRControl.MSG_RET_TRANSMIT_IR:
				rid = (UUID) msg.getData().getSerializable(
						CIRControl.KEY_RESULT_ID);
				Log.e(TAG, "Send IR Returned UUID: " + rid);
				// text1.setText("Send IR error=" + msg.arg1);
				switch (msg.arg1) {
				case CIRControl.ERR_IO_ERROR:
					// TODO:
					// CIR hardware component is busy in doing early CIR
					// command.
					break;
				case CIRControl.ERR_INVALID_VALUE:
					// TODO:
					// The developer might use wrong arguments.
					break;
				case CIRControl.ERR_CMD_DROPPED:
					// TODO:
					// SDK might be too busy to send IR key, developer can try
					// later, or send IR key with non-droppable setting
					break;
				default:
					// TODO: other errors
					break;
				}
				break;
			case CIRControl.MSG_RET_CANCEL:
				switch (msg.arg1) {
				case CIRControl.ERR_IO_ERROR:
					// TODO:
					// CIR hardware component is busy in doing early CIR
					// command.
					break;
				case CIRControl.ERR_CANCEL_FAIL:
					// TODO:
					// CIR hardware component is busy in doing early CIR
					// command.
					break;
				default:
					// TODO: other errors
					break;
				}
				break;

			/*
			 * case CIRControl.MSG_RET_STARTED:
			 * 
			 * Log.e(TAG, "ap version = " + CIRControl.VERSION); break;
			 * 
			 * case CIRControl.MSG_RET_CIR_VERSION: //CIR APP developer must
			 * check version of HTC_CIR to decide which api they can use. //The
			 * CIRControl.MSG_RET_CIR_VERSION will be sent after connecting to
			 * CIR service . //If developer can't receive this message after
			 * connecting , it means CIR service in this device is in first
			 * version (version code : 1) . int cirVersion = msg.arg2;
			 * Log.e(TAG, "CIR version = " + cirVersion); break;
			 */
			default:
				super.handleMessage(msg);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(sendIRcode);

		btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(sendIRcode);

		btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(sendIRcode);

		btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(sendIRcode);

		btn5 = (Button) findViewById(R.id.button5);
		btn5.setOnClickListener(sendIRcode);

		btn6 = (Button) findViewById(R.id.button6);
		btn6.setOnClickListener(sendIRcode);

		btn7 = (Button) findViewById(R.id.button7);
		btn7.setOnClickListener(sendIRcode);

		btn8 = (Button) findViewById(R.id.button8);
		btn8.setOnClickListener(sendIRcode);

		btn9 = (Button) findViewById(R.id.button9);
		btn9.setOnClickListener(sendIRcode);

		btn10 = (Button) findViewById(R.id.button10);
		btn10.setOnClickListener(sendIRcode);

		btn11 = (Button) findViewById(R.id.button11);
		btn11.setOnClickListener(sendIRcode);

		btn12 = (Button) findViewById(R.id.button12);
		btn12.setOnClickListener(sendIRcode);

		btn13 = (Button) findViewById(R.id.button13);
		btn13.setOnClickListener(sendIRcode);

		btn14 = (Button) findViewById(R.id.button14);
		btn14.setOnClickListener(sendIRcode);

		btn15 = (Button) findViewById(R.id.button15);
		btn15.setOnClickListener(sendIRcode);

		btn16 = (Button) findViewById(R.id.button16);
		btn16.setOnClickListener(sendIRcode);

		btn17 = (Button) findViewById(R.id.button17);
		btn17.setOnClickListener(sendIRcode);

		mControl = new CIRControl(getApplicationContext(), mHandler);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	class LooperThread extends Thread {
		public Handler mHandler;

		public void run() {
			Looper.prepare();

			mHandler = new Handler();

			Looper.loop();
		}
	}

	private OnClickListener sendIRcode = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn1) {
				freq = 30000;
			} else if (v == btn2) {
				freq = 31000;
			} else if (v == btn3) {
				freq = 32000;
			} else if (v == btn4) {
				freq = 33000;
			} else if (v == btn5) {
				freq = 34000;
			} else if (v == btn6) {
				freq = 35000;
			} else if (v == btn7) {
				freq = 36000;
			} else if (v == btn8) {
				freq = 37000;
			} else if (v == btn9) {
				freq = 38000;
			} else if (v == btn10) {
				freq = 39000;
			} else if (v == btn11) {
				freq = 40000;
			} else if (v == btn12) {
				freq = 25000;
			} else if (v == btn13) {
				freq = 26000;
			} else if (v == btn14) {
				freq = 27000;
			} else if (v == btn15) {
				freq = 28000;
			} else if (v == btn16) {
				freq = 29000;
			} else if (v == btn17) {
				freq = 38000;
				poc=true;
			}

			// TODO: To demonstrate how to use CIR SDK to send an IR key
			if (run == 0) {
				run = 1;
				listener();
				new Thread(new Runnable() {
					public void run() {

						// proof of concept tv
						int[] mod1 = { 335, 163, 24, 17, 24, 17, 24, 57, 24,
								17, 24, 17, 24, 17, 24, 17, 24, 17, 24, 57, 24,
								57, 24, 17, 24, 57, 24, 57, 24, 57, 24, 57, 24,
								57, 24, 17, 24, 17, 24, 17, 24, 57, 24, 17, 24,
								17, 24, 17, 24, 17, 24, 57, 24, 57, 24, 57, 24,
								17, 24, 57, 24, 56, 26, 56, 24, 57, 24, 3000 };
						// unlock signal
						int[] mod2 = { 5, 67, 5, 14, 2, 51, 5, 67, 5, 67, 5,
								13, 3, 51, 5, 67, 5, 67, 5, 67, 5, 13, 4, 49,
								5, 67, 5, 67, 5, 14, 2, 51, 5, 67, 5, 67, 5,
								67, 5, 14, 2, 51, 5, 14, 3, 50, 5, 67, 5, 13,
								2, 51, 5, 67, 5, 67, 5, 13, 4, 49, 5, 13, 3,
								50, 5, 13, 3, 50, 5, 67, 5, 14, 3, 50, 5, 67,
								5, 67, 5, 13, 4, 49, 6, 66, 5, 67, 5, 67, 5,
								13, 4, 49, 6, 66, 5, 67, 5, 13, 4, 50, 5, 67,
								5, 67, 5, 67, 5, 14, 3, 50, 5, 13, 5, 49, 5,
								67, 5, 13, 3, 50, 5, 67, 6, 66, 6, 13, 3, 3000 };

						if (poc) {
							HtcIrData ird = new HtcIrData(1, freq, mod1);
							UUID rid = mControl.transmitIRCmd(ird, true);
							mRIDs.add(rid);
							poc = false;
						} else {
							HtcIrData ird = new HtcIrData(1, freq, mod2);
							UUID rid = mControl.transmitIRCmd(ird, true);
							mRIDs.add(rid);	
						}	
						
						run = 0;
					}
				}).start();
			}
		}
	};

	private void listener() {
		if (mControl != null) {
			mControl.start();
		}
	};

}
