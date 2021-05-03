using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public GameObject effect;

    void OnCollisionEnter2D(Collision2D colInfo)
    {
        if(colInfo.collider.name == "Ground")
        {
            endgame();
        }
    }

    void endgame()
    {
        DeathSoundScript.PlaySound();
        Instantiate(effect, transform.position, Quaternion.identity);
        Destroy(gameObject);
    }
}
