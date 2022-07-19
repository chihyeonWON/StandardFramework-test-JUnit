package com.rubypaper.junit;

public class MyVector {
	protected Object elementData[];
	protected int elementCount;
	
	public MyVector(int intialCapacity) {
		this.elementData = new Object[intialCapacity];
	}
	public MyVector() {
		this(10);
	}
	
	public synchronized int size() {
		return elementCount;
	}
	
	public boolean isEmpty() {
		return elementCount ==0;
	}
	
	public boolean contains(Object elem) {
		return indexOf(elem) >= 0;
	}
	
	public int indexOf(Object elem) {
		for (int i=0; i<elementCount; i++) 
			if(elem.equals(elementData[i]))
				return i;
		return -1;
	}
	
	public synchronized Object elementAt(int index) {
		return elementData[index];
	}
	
	public synchronized void addElement(Object obj) {
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = obj;
	}
	
	private void ensureCapacityHelper(int minCapacity) {
		int oldCapacity = elementData.length;
		if(minCapacity > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = oldCapacity * 2;
			if(newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			elementData = new Object[newCapacity];
			System.arraycopy(oldData,  0,  elementData, 0, elementCount);
		}
	}
}

