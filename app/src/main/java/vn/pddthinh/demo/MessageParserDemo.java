package vn.pddthinh.demo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import vn.pddthinh.parserlib.MessageParser;

/**
 * Main activity
 */
public class MessageParserDemo extends Activity implements AdapterView.OnItemClickListener {
	private static final String TAG = MessageParserDemo.class.getSimpleName();

	/**
	 * Message file path
	 */
	private static final String MESSAGE_FILE = "/sdcard/messages.txt";

	/**
	 * Message adapter
	 */
	private MessageAdapter mAdapter = null;
	/**
	 * JSON result text view
	 */
	private EditText mtxtJsonView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message_parser_demo);

		setupView();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mAdapter.isEmpty()) {
			MessageLoader loader = new MessageLoader();
			loader.execute(MESSAGE_FILE);
		}
	}

	/**
	 * Setting up all views
	 */
	private void setupView() {
		ListView lsMessage = (ListView) findViewById(R.id.lst_message);
		mAdapter = new MessageAdapter(getApplicationContext(),
				R.layout.list_item,
				R.id.list_item_text);
		lsMessage.setAdapter(mAdapter);
		lsMessage.setOnItemClickListener(this);

		mtxtJsonView = (EditText) findViewById(R.id.txt_json);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		do {
			// get selected string
			String msg = (String) parent.getItemAtPosition(position);

			// call parser library
			MessageParser parser = new MessageParser();
			JSONObject json = parser.parseJSON(msg);

			// re-format the json result
			String jText = null;
			try {
				jText = json.toString(3);
				jText = jText.replaceAll("\\\\/", "/");
			}
			catch (Exception ex) {
			}
			if (jText == null)
				jText = getString(R.string.err_no_element);
			mtxtJsonView.setText(jText);
		}
		while (false);
	}

	/**
	 * Clean the json result text view
	 */
	private void reset() {
		mtxtJsonView.setText(null);
	}

	/**
	 * Task to load messages from data file
	 */
	class MessageLoader extends AsyncTask<String, Void, ArrayList<String>> {
		private String mError = null;
		private boolean mFileExisted = true;

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			ArrayList<String> messages = null;

			do {
				if (params == null || params.length != 1) {
					Log.e(TAG, "Invalid loader params!");
					break;
				}

				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(params[0]));
					messages = new ArrayList<>();

					String msg;
					while ((msg = br.readLine()) != null) {
						if (msg.length() == 0)
							continue;

						messages.add(msg);
					}
				}
				catch (FileNotFoundException ex) {
					mFileExisted = false;
					Log.e(TAG, "File not existed!");
				}
				catch (Exception ex) {
					mError = ex.getMessage();
					Log.e(TAG, "Failed to load data from file: " + mError);
					messages = null;
				}
				finally {
					try {
						br.close();
					}
					catch (Exception ex) {
					}
				}
			}
			while (false);

			return messages;
		} // doInBackground

		@Override
		protected void onPostExecute(ArrayList<String> messages) {
			do {
				// error occurs?
				if (messages == null) {
					// file existed?
					if (mFileExisted == false) {
						TextView tvError = (TextView) findViewById(R.id.txt_error);

						tvError.setVisibility(View.VISIBLE);
						tvError.setText(Html.fromHtml(getString(R.string.err_msg_file_not_found)));
						break;
					}

					// other error
					if (mError != null && mError.length() > 0)
						Toast.makeText(getApplicationContext(), mError, Toast.LENGTH_LONG).show();
					break;
				}

				// no error
				findViewById(R.id.main_container).setVisibility(View.VISIBLE);
				findViewById(R.id.txt_error).setVisibility(View.GONE);

				// reset the adapter data
				synchronized (mAdapter) {
					mAdapter.setNotifyOnChange(false);

					mAdapter.clear();
					for (String msg : messages)
						mAdapter.add(msg);
				}

				reset();
				mAdapter.notifyDataSetChanged();
			}
			while (false);
		}
	} // MessageLoader

	/**
	 * Custom message adapter
	 */
	class MessageAdapter extends ArrayAdapter<String> {
		private LayoutInflater mInflater = null;

		public MessageAdapter(Context context, int layoutId, int txtViewId) {
			super(context, layoutId, txtViewId);

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null)
				view = mInflater.inflate(R.layout.list_item, null);

			TextView tv = (TextView) view.findViewById(R.id.list_item_text);
			tv.setText(getItem(position));

			return view;
		}
	} // MessageAdapter
}
