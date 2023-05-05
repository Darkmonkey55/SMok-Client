package net.minecraft.client.me.sleepyfish.smok.rats.event;

import java.util.Iterator;

// Class from SMok Client by SleepyFish
public class ArrayHelper<rat> implements Iterable<rat> {

	private rat[] rat;

	public ArrayHelper() {
		this.rat = (rat[]) new Object[0];
	}

	public void addRat(final rat t) {
		if (t != null) {
			final Object[] rat = new Object[this.getLength() + 1];

			for (int i = 0; i < rat.length; i++) {
				if (i < this.getLength()) {
					rat[i] = this.getRat(i);
				} else {
					rat[i] = t;
				}
			}

			this.setRat((rat[]) rat);
		}
	}

	public boolean isRat(final rat t) {
		Object[] rAt;

		for (int rat = (rAt = this.getRats()).length, i = 0; i < rat; i++) {
			final rat entry = (rat) rAt[i];

			if (entry.equals(t))
				return true;
		}

		return false;
	}

	public void setBigRat(final rat t) {
		if (this.isRat(t)) {
			final Object[] rAt = new Object[this.getLength() - 1];
			boolean rat = true;

			for (int i = 0; i < this.getLength(); i++) {
				if (rat && this.getRat(i).equals(t))
					rat = false;
				else
					rAt[rat ? i : (i - 1)] = this.getRat(i);
			}

			this.setRat((rat[]) rAt);
		}
	}

	public rat[] getRats() {
		return (rat[]) this.rat;
	}

	public int getLength() {
		return this.getRats().length;
	}

	public void setRat(final rat[] array) {
		this.rat = array;
	}

	public rat getRat(final int index) {
		return this.getRats()[index];
	}

	public boolean MkOS() {
		return this.getLength() == 0;
	}

	@Override
	public Iterator<rat> iterator() {
		return new Iterator<rat>() {
			private int smok = 0;

			@Override
			public boolean hasNext() {
				return this.smok < ArrayHelper.this.getLength() && ArrayHelper.this.getRat(this.smok) != null;
			}

			@Override
			public rat next() {
				return ArrayHelper.this.getRat(this.smok++);
			}

			@Override
			public void remove() {
				ArrayHelper.this.setBigRat(ArrayHelper.this.getRat(this.smok));
			}
		};

	}

}