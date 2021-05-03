using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundManagerScript : MonoBehaviour
{
    public static AudioClip hitSound;
    static AudioSource audioSrc;
    // Start is called before the first frame update
    void Start()
    {
        hitSound = Resources.Load<AudioClip>("woodhit");

        audioSrc = GetComponent<AudioSource> ();
    }

    // Update is called once per frame
    public static void PlaySound()
    {
        audioSrc.PlayOneShot (hitSound);
    }
}
