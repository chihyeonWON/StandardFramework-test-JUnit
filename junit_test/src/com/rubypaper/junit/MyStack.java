package com.rubypaper.junit;

public class MyStack {
	private Object[] items;
	private int total;
	
	public MyStack() {
		items = new Object[10];
		total = -1;
	}
	
	public MyStack(int num) {
		if(num > 0)
			items = new Object[num];
		else
			items = new Object[10];
		total = -1;
	}
	
	public void push(Object obj) {
		isFull(total);
		items[++total] = obj;
	}
	
	public boolean isEmpty() {
		return total == -1;
	}
	
	private void isFull(int min) {
		int minCapacity = min + 1;
		int oldCapacity = items.length;
		if(minCapacity > oldCapacity) {
			Object oldData[] = items;
			int newCapacity = oldCapacity * 2;
			if(newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			items = new Object[newCapacity];
			System.arraycopy(oldData, 0, items, 0, items.length);
		}
	}
	public Object top() {
		if(isEmpty())
			return null;
		return items[total];
	}
	public Object pop() {
		if(isEmpty())
			return null;
		Object obj = items[total];
		items[total] = null;
		total--;
		return obj;
	}
	public int size() {
		return total;
	}
}
