using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DeathSoundScript : MonoBehaviour
{
    public static AudioClip deathS;
    static AudioSource audioSrc;
    // Start is called before the first frame update
    void Start()
    {
        deathS = Resources.Load<AudioClip>("DeathSound");

        audioSrc = GetComponent<AudioSource> ();
    }

    // Update is called once per frame
    public static void PlaySound()
    {
        audioSrc.PlayOneShot (deathS);
    }
}
