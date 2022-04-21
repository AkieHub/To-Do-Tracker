import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModfifyTaskComponent } from './modfify-task.component';

describe('ModfifyTaskComponent', () => {
  let component: ModfifyTaskComponent;
  let fixture: ComponentFixture<ModfifyTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModfifyTaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModfifyTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
