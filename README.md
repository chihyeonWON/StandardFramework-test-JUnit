# StandardFramework-test-JUnit
표준프레임워크 테스트 도구인 JUnit Studying

## JUnit 라이브러리 사용

JUnit을 사용하기 위해 라이브러리를 추가하는 방법은 여러 가지가 있다.

1. 클래스 패스 추가
2. 이클립스에서 라이브러리 추가
3. 메이븐의 디펜더시 설정

세가지 방법 중에 한 가지를 통해 라이브러리를 추가하고 나면 다음 라이프 사이클에 의해서 코드를 작성하면 된다.
JUnit 4.x 버전에서는 어노테이션(Annotation)을 사용하며 라이프 사이클은 다음과 같다.

## JUnit 라이프 사이클

@BeforeClass(해당 테스트 클래스가 실행되기 이전의 수행할 코드 구현)
@Before(테스트 작성 전에 수행할 코드 구현)
@Test(테스트 코드 구현) assert 메소드를 사용하여 테스트 로직 구현
@After(테스트 종료 전에 수행할 코드 구현)
@AfterClass(해당 테스트 클래스가 실행된 이후에 수행할 작업 구현)

## JUnit 테스트 케이스 작성

이클립스에서 라이브러리를 추가하는 방식으로 진행한다.

New -> Java Project로 자바프로젝트를 생성하고 생성 시에 라이브러리를 추가한다.
JUnit 라이브러리를 추가하는 방법은 Java Settings -> Libraries -> Add Library -> JUnit 4 로 추가한다.

패키지를 하나 생성한 뒤에 이 패키지 밑에 두 개의 클래스를 생성한다.

1. MyVector 클래스는 Object의 배열을 생성하고 비어 있는지 체크, 요소(배열의 엘리먼트) 추가, 요소 포함 여부 확인, 사이즈 확인하는 메소드 등으로 구성된 코드이다.

MyVector.java
```
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
```

MyStack 클래스는 Object의 배열을 생성하고, 넣고 꺼내는 등의 메소드로 구성된다.
```
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
```

일반적으로 개발 시 소스 코드와 테스트 코드를 분리하고, 배포 시에도 테스트 코드를 분리하기 위해서 별도의 폴더를 만들어서 관리하는 것이 좋다.
따라서 프로젝트를 선택하고 폴더를 생성한 뒤 패키지를 생성하고 MyVectorTest JUnit Test Case를 생성한다.
Which method stubs would you like to create? 에서 모든 메소드를 체크한다.

class under test 에서 MyVector 클래스를 선택한다.

MyVectorTest 테스트 케이스
```
package com.rubypaper.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyVectorTest {
	private MyVector v;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass 수행");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass 수행");
	}

	@Before
	public void setUp() throws Exception {
		v= new MyVector();
		System.out.println("setUp 수행");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown 수행");
	}

	@Test
	public void testIsEmpty() {
		assertTrue(v.isEmpty());
		v.addElement("abc");
		assertTrue(!v.isEmpty());
		System.out.println("testIsEmpty 수행");
	}

	@Test
	public void testContains() {
		String str1 = new String("abc");
		v.addElement(str1);
		assertTrue(v.contains(str1));
		System.out.println("testContains 수행");
	}

	@Test
	public void testIndexOf() {
		String str1 = new String("abc");
		String str2 = new String("def");
		v.addElement(str1);
		v.addElement(str2);
		assertEquals(1, v.indexOf(str2));
		System.out.println("testIndexOf 수행");
	}

	@Test
	public void testElementAt() {
		String str1 = new String("abc");
		String str2 = new String("def");
		v.addElement(str1);
		v.addElement(str2);
		assertSame(str1, v.elementAt(0));
		assertSame(str2, v.elementAt(1));
		System.out.println("testElement 수행");
	}

	@Test
	public void testAddElement() {
		v.addElement("abc");
		v.addElement(new Integer(1));
		assertEquals(2, v.size());
		System.out.println("testAddElement 수행");
	}

}
```
코드를 작성한 후 Run As -> JUnit Test를 실행하여 결과를 확인한다.

다음과 같이 5개의 테스트 메소드 중에서 5개를 실행했으며, 에러와 실패가 모두 0으로 나타난다. 각 테스트 메소드들은 서로 독립적으로 수행된다.
![JUnit 실행](https://user-images.githubusercontent.com/58906858/179663369-1daef9e8-5d1b-46bc-8e1b-762224689da7.png)

마찬가지로 MyStackTest 이름을 가지는 테스트 케이스를 생성한 후 setUp() 메소드만을 선택하고 생성한다.

MyStackTest
```
package com.rubypaper.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyStackTest {
	private MyStack s;
	
	@Before
	public void setUp() throws Exception {
		s = new MyStack();
	}

	@Test
	public void testPush() {
		System.out.println("testPush");
		s.push("abc");
		s.push("def");
		s.push(new Integer(1));
		assertEquals(2, s.size());
	}

	@Test
	public void testIsEmpty() {
		System.out.println("testIsEmpty");
		assertTrue(s.isEmpty());
		s.push("abc");
		assertTrue(!s.isEmpty());
	}

	@Test
	public void testPop() {
		System.out.println("testPop");
		s.push("abc");
		s.push("def");
		String str = (String) s.pop();
		assertEquals("def", str);
		assertEquals(0, s.size());
	}
}
```

JUnit 실행 결과는 다음과 같다.

![JUnit MyStackTest](https://user-images.githubusercontent.com/58906858/179664417-d9ca8ff0-cc6d-4c6a-979f-e456d15b93d8.png)

## 테스트 스위트 작성

MyVectorTest와 MyStackTest 두 개의 테스트 케이스를 실행하기 위해서는 각각의 클래스를 실행해야 한다. 테스트 케이스마다 별도로 테스트를 수행한다면 한 패키지와 모듈 단위로 테스트하거나 테스트를 자동화하는 것이 어려워진다. 

프로젝트를 수행하다 보면 테스트 케이스 하나씩 실행하기보다는 패키지 단위로 컴포넌트 단위로 묶어서 테스트해야 하는 경우가 많다. 이런 경우 테스트 스위트 (Test Suite)를 만들어 한 번에 수행할 수 있다. 

테스트 케이스가 있는 패키지를 선택하고 New -> Other -> Java -> JUnit -> JUnit Test Suite를 선택하여 테스트 스위트를 만든다.
스위트에 포함하고 싶은 테스트 케이스를 선택하여 포함한다.

이렇게 하면 자동으로 코드가 생성되고 추가 코드 없이 바로 실행된다. 생성된 AllTests 클래스를 선택한 후 Run As -> JUnit Test를 수행하면 다음과 같다.
![JUnit 테스트 스위트](https://user-images.githubusercontent.com/58906858/179665148-1c937a53-b2de-4f2f-bcf4-da1303555b0c.png)

이렇게 생성된 코드는 한 번만 실행하면 되고 엔트나 메이븐으로 자동화하기도 간편하다.
