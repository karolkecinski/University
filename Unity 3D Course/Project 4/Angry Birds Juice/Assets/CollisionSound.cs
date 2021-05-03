using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollisionSound : MonoBehaviour
{
    public AudioSource clSound;

    void Start()
    {
        clSound = GetComponent<AudioSource>();
    }

    void OnCollisionEnter(Collision col)
    {
        clSound.Play();
    }

    void Update()
    {
        
    }
}
