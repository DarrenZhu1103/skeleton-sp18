public class LinkedListDeque<T> {
    private class LinkList{
        private T first;
        private LinkList rest, before;

        public LinkList(T first, LinkList rest, LinkList before){
            this.first = first;
            this.rest = rest;
            this.before = before;
        }

        public LinkList(){
            this(null, null, null);
        }

        public T getFirst() {
            return this.first;
        }

        public LinkList getRest(){
            return this.rest;
        }

        public LinkList getBefore() {
            return before;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setRest(LinkList rest) {
            this.rest = rest;
        }

        public void setBefore(LinkList before) {
            this.before = before;
        }

        public void setRestReverse(LinkList rest) {
            this.rest = rest;
            rest.setBefore(this);
        }

        public void setBeforeReverse(LinkList before) {
            this.before = before;
            before.setRest(this);
        }
    }

    private LinkList lst;
    private int size;

    public LinkedListDeque(){
        this.lst = new LinkList();
        this.lst.setRestReverse(this.lst);
        this.size = 0;
    }

    public void addFirst(T item){
        this.size += 1;
        LinkList lnk = new LinkList(item, null, null);
        lnk.setRestReverse(this.lst.getRest());
        lnk.setBeforeReverse(this.lst);
    }

    public void addLast(T item){
        this.size += 1;
        LinkList lnk = new LinkList(item, null, null);
        lnk.setBeforeReverse(this.lst.getBefore());
        lnk.setRestReverse(this.lst);
    }

    public boolean isEmpty(){
        if (this.size == 0)
            return true;
        return false;
    }

    public int size(){
        return this.size;
    }

    public void printDeque(){
        if (isEmpty()){
            System.out.println();
            return;
        }
        LinkList lnk = this.lst.getRest();
        while (lnk != this.lst.getBefore()){
            System.out.print(lnk.getFirst() + " ");
            lnk = lnk.getRest();
        }
        System.out.println(lnk.getFirst().toString());
    }

    public T removeFirst(){
        if (this.size == 0)
            return null;
        T result = this.lst.getRest().getFirst();
        this.lst.setRestReverse(this.lst.getRest().getRest());
        this.size -= 1;
        return result;
    }

    public T removeLast(){
        if (this.size == 0)
            return null;
        T result = this.lst.getBefore().getFirst();
        this.lst.setBeforeReverse(this.lst.getBefore().getBefore());
        this.size -= 1;
        return result;
    }

    public T get(int index){
        LinkList lnk = this.lst.getRest();
        int i = 0;
        while (lnk != this.lst) {
            if (i == index)
                return lnk.getFirst();
            i += 1;
        }
        return null;
    }

    public T getRecursive(int index){
        return getRecursiveHelper(index, this.lst.getRest());
    }

    private T getRecursiveHelper(int index, LinkList lnk){
        if (lnk == this.lst)
            return null;
        if (index == 0)
            return lnk.getFirst();
        return getRecursiveHelper(index - 1, lnk.getRest());
    }

}