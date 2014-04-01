package com.ece1778.myally.ui;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class QuoteSwitcher {
	//Fade in/out duration for quotes in milliseconds
	private int _fadeDuration = 2000;
	//Duration of quotes in milliseconds
	private int _quoteDuration = 6000;
	//TextSwitcher used to display quotes in View
	private TextSwitcher _textSwitcher;
	//Array of quotes
	private String[] _quotes;
	//Runs threads, such as one for switching quotes
	private Handler _handler = null;

	public QuoteSwitcher(final Context context, TextSwitcher quotesSwitcher, 
			String[] quotes) {
		_textSwitcher = quotesSwitcher;
		_quotes = quotes;
		
		//Setup the text size and orientation
		_textSwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				TextView quote = new TextView(context);
				quote.setGravity(Gravity.CENTER);
				quote.setTextColor(Color.WHITE);
				quote.setTextAppearance(context, 
						android.R.style.TextAppearance_Large);
				quote.setTypeface(null, Typeface.ITALIC);
				return quote;
			}
		});
		
		_handler = new Handler();
	}

	public void init() {
		//Fade In Animation
		Animation in;
		in = new AlphaAnimation (0.0f, 1.0f);
		in.setDuration(_fadeDuration);
		_textSwitcher.setInAnimation(in);
		//Fade Out Animation
		Animation out;
		out = new AlphaAnimation(1.0f, 0.0f);
		out.setDuration(_fadeDuration);
		_textSwitcher.setOutAnimation(out);

		//Show first quote right away
		_handler.postDelayed(_updateQuoteTask, 0);
	}

	public void setFadeDuration(int fadeDuration) {
		_fadeDuration = fadeDuration;
	}

	public void setQuoteDuration(int quoteDuration) {
		_quoteDuration = quoteDuration;
	}

	public void setQuotes(String[] quotes) {
		_quotes = quotes;
	}

	private Runnable _updateQuoteTask = new Runnable() {
		public void run() {
			//Pick one quote
			int numQuotes = _quotes.length;
			Random r = new Random();
			int index = r.nextInt(numQuotes);

			//Display the quote
			_textSwitcher.setText(String.valueOf(_quotes[index]).trim());

			//Select a new quote after _quoteDuration milliseconds
			_handler.postDelayed(this, _quoteDuration);
		}
	};
}
