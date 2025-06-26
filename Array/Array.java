/*          动态数组 Array
        实现一个动态数组，支持基本的增删查改操作
        支持泛型，能够存储任意类型的数据
        支持自动扩容和缩容
*/
public class Array<T> {
    private T[] data;   //用于保存数据
    private int size;   //存放元素个数

    //Constructor 构造函数
    @SuppressWarnings("unchecked")
    public Array(int capacity) {
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    //default constructor 默认构造函数
    public Array() {
        this(10); // 默认容量为10
    }

    //derive the capacity of the array
    public int getCapacity(){
        return data.length;
    }

    //derive the numbers of elements
    public int getSize(){
        return size;
    }

    //judge if the array is empty
    public boolean isEmpty(){
        return size==0;
    }

    //插入一个新元素在数组索引index位置
    public void insert(int index, T element){
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Index out of range: require index >=0 and index <= size.");
        }

        if(size == data.length) 
            this.resize(2 * data.length); // 扩容

        for (int i = size-1; i >= index; i--)
            data[i+1] = data[i];
        
        data[index] = element;
        size++;
    }

/*      关于添加新元素在队末时的复杂度分析：
    假设capacity为n，n+1次addLast()操作，触发resize()方法，扩容2倍
    （将元素转移至新的2n空间，逐个转移，复杂度为O(n)）
    总共进行了2n+1次基本操作，平均来讲，每次addLast()操作，进行了2次基本操作，
    这样也就意味着，均摊下来的addLast()方法的复杂度为O(1)，
    而不是之前分析的O(n)，这样的均摊复杂度显然比最坏复杂度来得更有意义，
    因为不是每一次的操作都是最坏的情况！
*/

    //插入一个新元素在数组末尾
    public void addLast(T element){
        insert(size, element);
    }

    //插入一个新元素在数组头部
    public void addFirst(T element){
        insert(0, element);
    }

    //查找：获取index索引位置的元素
    public T get(int index){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    //修改index索引位置的元素为element
    public void set(int index, T element){
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = element;
    }

    //查找数组中是否有元素element
    public boolean contains(T element){
        for (int i = 0; i < size; i++){
            if(data[i].equals(element))
                return true;
        }
        return false;
    }

    //查找数组中元素的索引，如果不存在元素则返回-1
    public int find(T element){
        for (int i = 0; i < size; i++){
            if (data[i].equals(element))
            return i;
        }
        return -1;
    }

    //删除数组中index位置的元素，并返回删除的元素
    public T remove(int index){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Index is illegal.");

        T ret = data[index];
        for (int i = index + 1; i < size; i++)
            data[i-1] = data[i];
        size--;
        data[size] = null; // 避免对象游离(loitering object),保证垃圾回收

        /*在数组长度变成1/4时才缩容：
            有效避免“复杂度震荡”问题，即频繁扩容和缩容导致的性能波动。
            只有当元素数量降到容量的四分之一时才缩容，
            能保证数组不会因为偶尔的删除操作而频繁调整容量。*/
        if(size == data.length / 4 && data.length / 2 != 0) 
            this.resize(data.length / 2); // 缩容
        return ret;
    }

    //删除数组中第一个元素, 返回删除的元素
    public T removeFirst() {
        return remove(0);
    }

    //删除数组中最后一个元素, 返回删除的元素
    public T removeLast() {
        return remove(size - 1);
    }

    // 删除数组中元素e
    public void removeElement(T element) {
        int index = find(element);
        if (index != -1)
            remove(index);
    }

    // 输出数组信息
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }

    // 扩容或缩容数组
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    //test client
    public static void main(String[] args){
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 10; i++) {
            array.addLast(i);
        }
        System.out.println("Initialized: 0 to 9 in the array.");
        array.insert(2, 100); //在索引2位置插入100
        System.out.println(array.toString());
        //测试输入、删除、扩容缩容、查找、修改等操作
        array.addFirst(20);
        array.addLast(50);
        System.out.println(array.toString());
        array.remove(3); //删除索引3位置的元素
        System.out.println(array.toString());
        System.out.println("Contains 50? " + array.contains(50)); //查找是否包含50
        array.resize(30);
        array.set(0, 99); //修改索引0位置的元素为99
        System.out.println(array.toString());
        System.out.println("Terminal array: " + array.toString());
        System.out.println(" || Test ended. || ");
    }
}
