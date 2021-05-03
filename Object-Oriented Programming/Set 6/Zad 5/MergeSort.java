class MergeSort extends Thread
{

  public int[] tab;

  public MergeSort(int[] GetVals)
  {
    tab = GetVals;
  }

  public void mergeSort(int[] tab)
  {

    if (tab.length > 1)
    {
      
      int[] left = leftTab(tab);
      int[] right = RightTab(tab);

      MergeSort sort1 = new MergeSort(left);
      MergeSort sort2 = new MergeSort(right);

      sort1.start();
      sort2.start();

      try
  	  {
  		  sort1.join();
        sort2.join();
  	  }
      catch(Exception e)
  	  {
  		System.out.print("Error");
  	  }

      merge(tab, left, right);
    }
  }

  public int[] leftTab(int[] tab)
  {

      int size = tab.length / 2;
      int[] left = new int[size];

      for (int i = 0; i < size; i++)
      { left[i] = tab[i]; }
      
      return left;
  }

  public int[] RightTab(int[] tab)
  {

      int size = tab.length / 2;
      int this_size = tab.length - size;

      int[] right = new int[this_size];

      for (int i = 0; i < this_size; i++)
      { right[i] = tab[i + size]; }
        
      return right;
  }

  public void merge(int[] result, int[] left, int[] right)
  {

    int left_iter = 0;
    int right_iter = 0;

    for (int i = 0; i < result.length; i++)
    {
      if (right_iter >= right.length || 
            (left_iter < left.length && left[left_iter] <= right[right_iter]))
      {
         result[i] = left[left_iter];
         left_iter++;
      }
      else
      {
         result[i] = right[right_iter];
         right_iter++;
      }
    }
  }

  public void run()
  {
    mergeSort(tab);
  }
}