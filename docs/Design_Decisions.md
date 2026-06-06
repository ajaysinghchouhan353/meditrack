# Design Decisions - MediTrack

## Overview

This document records the main architectural choices used in MediTrack.

## Decisions

- **Layering**: UI, service, repository, and domain layers are separated for clarity.
- **Persistence**: CSV export/import is paired with Java serialization for quick local restore.
- **Patterns**: Singleton, Factory, Strategy, Observer, Repository, and Template Method are used where they fit the domain.
- **Cloning**: Entities that support duplication use explicit copy construction to keep behavior predictable.
- **Enums**: Domain enums are used for appointment status and medical specialties to keep inputs type-safe.

## Notes

- `DataStore<T>` centralizes in-memory storage and serialization.
- `Main` owns application wiring and persistence bootstrapping.