using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    void OnCollisionEnter2D(Collision2D colInfo)
    {
        if(colInfo.collider.name == "Ground")
        {
            endgame();
        }
    }

    void endgame()
    {
        Destroy(gameObject);
    }
}
