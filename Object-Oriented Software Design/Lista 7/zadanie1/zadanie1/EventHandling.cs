namespace EventHandling
{
    public interface ISubscriber<T>
    {
        void Handle(T notification);
    }

    public interface IEventAggregator
    {
        void AddSubscriber<T>(ISubscriber<T> subscriber);
        void RemoveSubscriber<T>(ISubscriber<T> subscriber);
        void Publish<T>(T Event);
    }

    public class EventAggregator : IEventAggregator
    {
        Dictionary<Type, List<object>> _subscribers =
            new Dictionary<Type, List<object>>();

        #region IEventAggregator Members

        public void AddSubscriber<T>(ISubscriber<T> subscriber)
        {
            if (!_subscribers.ContainsKey(typeof(T)))
                _subscribers.Add(typeof(T), new List<object>());

            _subscribers[typeof(T)].Add(subscriber);
        }

        public void RemoveSubscriber<T>(ISubscriber<T> subscriber)
        {
            if (!_subscribers.ContainsKey(typeof(T)))
                _subscribers.[typeof(T)].Remove(subscriber);
        }

        public void Publish<T>(T Event)
        {
            if (_subscribers.ContainsKey(typeof(T)))
            {
                foreach (ISubscriber<T> subscriber in _subscribers[typeof(T)].OfType<ISubscriber<T>>())
                    subscriber.Handle(Event);
            }
        }

        #endregion
    }
}
