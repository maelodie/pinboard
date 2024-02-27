package pobj.pinboard.editor;

import java.util.*;
import pobj.pinboard.document.*;

public class Clipboard {
	private static Clipboard clipboard  = new Clipboard();
	private List<ClipboardListener> listenerList;
	private List<Clip> copied;

	private Clipboard() {
		copied = new ArrayList<Clip>();
		listenerList = new ArrayList<>();
	}
	public void copyToClipboard(List<Clip> clips) {
		for(Clip c : clips) {
			copied.add(c.copy());
		}
		notifyListeners();
	}

	public List<Clip> copyFromClipboard() {
		List<Clip> copy = new ArrayList<Clip>();

		for(Clip c : copied) {
			//c.move(20, 20); //coller à un endroit différent
			copy.add(c.copy());
		}

		return copy;
	}

	public void clear() {
		copied.clear();
		notifyListeners();
	}

	public boolean isEmpty() {
		return copied.isEmpty();
	}

	public static Clipboard getInstance() {
		return clipboard;
	}

	public void addListener(ClipboardListener listener) {
		listenerList.add(listener);
		
	}

	public void removeListener(ClipboardListener listener) {
		listenerList.remove(listener);
	}

	private void notifyListeners() {
		for (ClipboardListener listener : listenerList) {
			listener.clipboardChanged();
		}
	}
}
