using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerCollision : MonoBehaviour
{
    void OnCollisionEnter2D(Collision2D colInfo)
    {
        if(colInfo.collider.tag == "ColliderBlock")
        {
            SoundManagerScript.PlaySound();
        }
    }
}
